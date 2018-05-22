package com.denisg.pontointeligente.api.services;

import java.util.Optional;

import com.denisg.pontointeligente.api.entities.Empresa;

public interface EmpresaService {
	
	/**
	 * Retorna uma empresa dado o CNPJ
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 */
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	/**
	 * Cadastra uma nova empresa na base de dados
	 * 
	 * @param empresa
	 *@return Empresa
	 */
	Empresa persistir(Empresa empresa);

}
