package com.loiola.dantas.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.loiola.dantas.api.exception.ErroAutenticacaoException;
import com.loiola.dantas.api.exception.RegraNegocioException;
import com.loiola.dantas.api.model.entity.Usuario;
import com.loiola.dantas.api.model.repository.UsuarioRepository;
import com.loiola.dantas.api.service.UsuarioService;
import com.loiola.dantas.api.util.CpfCnpjUtils;
import com.loiola.dantas.api.util.TextoUtil;


@Service
public class UsuarioServiceImpl  implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Override
	public Usuario autenticar(String cpf, String senha) {
		Usuario usuario = usuarioRepository.findByCpf(cpf);
		if(usuario == null) {
			throw new ErroAutenticacaoException("Usuário não encontrado para o CPF informado.");
		}
		if(!encoder.matches(senha, usuario.getSenha())) {
			throw new ErroAutenticacaoException("Senha Inválida.");
		}
		return usuario;
	}
	
	
	
	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		this.validarUsuario(usuario, true);
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	@Override
	@Transactional
	public Usuario alterarUsuario(String cpf, Usuario usuario) {
		this.validarUsuario(usuario,false);
		Usuario usuarioConsultado = usuarioRepository.findByCpf(cpf);
		BeanUtils.copyProperties(usuario, usuarioConsultado, "codigo");
		usuarioConsultado.setSenha(encoder.encode(usuarioConsultado.getSenha()));
		return usuarioRepository.save(usuarioConsultado);
	}
	
	public boolean isCpfcadastrado(String cpf) {
		Usuario usuario = usuarioRepository.findByCpf(cpf);
		return usuario != null;
	}
	
	
	public void validarUsuario(Usuario usuario, boolean isInclusao) {
		String cpf = usuario.getCpf();
		if(TextoUtil.isNullOrEmpty(cpf)) {
			throw new RegraNegocioException("Informe o CPF.");
		}
		
		if(!CpfCnpjUtils.isValidCPF(usuario.getCpf())) {
			throw new RegraNegocioException("o CPF informado é inválido.");
		}
		
		if(isInclusao && isCpfcadastrado(cpf)) {
			throw new RegraNegocioException("Já existe cadastro para o CPF informado.");
		}
		
		if(!isInclusao && !isCpfcadastrado(cpf)) {
			throw new RegraNegocioException("Não existe cadastro para o CPF informado.");
		}
		
		if(TextoUtil.isNullOrEmpty(usuario.getNome())) {
			throw new RegraNegocioException("Informe o Nome.");
		}
		
		if(TextoUtil.isNullOrEmpty(usuario.getFone())) {
			throw new RegraNegocioException("Informe o telefone.");
		}
		if(!TextoUtil.isCelularValido(usuario.getFone())) {
			throw new RegraNegocioException("Telefone inválido.");
		}
		
		if(TextoUtil.isNullOrEmpty(usuario.getSenha())) {
			throw new RegraNegocioException("Informe a senha.");
		}
		
		
	}
	
}
