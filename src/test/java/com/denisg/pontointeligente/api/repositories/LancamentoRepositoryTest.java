package com.denisg.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.denisg.pontointeligente.api.entities.Empresa;
import com.denisg.pontointeligente.api.entities.Funcionario;
import com.denisg.pontointeligente.api.entities.Lancamento;
import com.denisg.pontointeligente.api.enums.PerfilEnum;
import com.denisg.pontointeligente.api.enums.TipoEnum;
import com.denisg.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private Long funcionarioId;
	
	@Before
	public void setUp() throws Exception{
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		
		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
		this.funcionarioId = funcionario.getId();
		
		this.lancamentoRepository.save(obeterDadosLancamentos(funcionario));
		this.lancamentoRepository.save(obeterDadosLancamentos(funcionario));
		
		
	}
	
	@After
	public void tearDown() throws Exception{
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscarLacamentosPorFuncionarioId() {
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
		
		assertEquals(2, lancamentos.size());
	}
	
	@Test
	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
		
		assertEquals(2, lancamentos.getTotalElements());
	}
	
	private Lancamento obeterDadosLancamentos(Funcionario funcionario) {
		
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);
		
		return lancamento;
	}
	
	private Empresa obterDadosEmpresa() {

		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj("51463645000100");

		return empresa;

	}

	private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {

		Funcionario funcionario = new Funcionario();

		funcionario.setNome("Funcionario Teste");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.geraBCrypt("123456"));
		funcionario.setCpf("55974952081");
		funcionario.setEmail("email@teste.com");
		funcionario.setEmpresa(empresa);

		return funcionario;
	}
	
}
