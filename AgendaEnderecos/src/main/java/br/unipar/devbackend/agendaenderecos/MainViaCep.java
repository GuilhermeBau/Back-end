package br.unipar.devbackend.agendaenderecos;

import br.unipar.devbackend.agendaenderecos.dao.ClienteDAO;
import br.unipar.devbackend.agendaenderecos.dao.EnderecoDAO;
import br.unipar.devbackend.agendaenderecos.model.Clientes;
import br.unipar.devbackend.agendaenderecos.model.Endereco;
import br.unipar.devbackend.agendaenderecos.service.ViaCepService;
import br.unipar.devbackend.agendaenderecos.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainViaCep {

    public static void main(String[] args) {
        EntityManager em = null;
        Scanner sc = null;

        try {
            em = EntityManagerUtil.getEm();
            EnderecoDAO enderecoDAO = new EnderecoDAO(em);
            ClienteDAO clienteDAO = new ClienteDAO(em);
            sc = new Scanner(System.in);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.print("Digite o CEP (apenas números): ");
            String cep = sc.nextLine();

            // 1. Consulta o CEP no banco de dados via Hibernate
            Endereco endereco = enderecoDAO.findByCep(cep);

            if (endereco != null) {
                // Caso 1: O CEP EXISTE no banco de dados
                System.out.println("\n✅ Endereço encontrado no banco de dados:");
                System.out.println("   Logradouro: " + endereco.getLogradouro());
                System.out.println("   Bairro: " + endereco.getBairro());
                System.out.println("   Cidade: " + endereco.getLocalidade());
                System.out.println("   UF: " + endereco.getUf());
                System.out.println("   Data de Cadastro: " + endereco.getDataCadastro());
            } else {
                System.out.println("\n⚠ Endereço não encontrado no banco. Consultando ViaCEP...");

                ViaCepService viaCepService = new ViaCepService();
                endereco = viaCepService.buscarCep(cep);
                endereco.setDataCadastro(LocalDateTime.now());

                System.out.println("   Dados do endereço da API ViaCEP:");
                System.out.println("   Logradouro: " + endereco.getLogradouro());
                System.out.println("   Bairro: " + endereco.getBairro());
                System.out.println("   Cidade: " + endereco.getLocalidade());
                System.out.println("   UF: " + endereco.getUf());

                enderecoDAO.gravar(endereco);
                System.out.println(" Novo endereço cadastrado com sucesso!");
            }

            System.out.println("\n➡ Agora, vamos cadastrar um novo cliente associado a este endereço.");
            Clientes cliente = new Clientes();
            System.out.print("   Nome do cliente: ");
            cliente.setNome(sc.nextLine());
            System.out.print("   Email do cliente: ");
            cliente.setEmail(sc.nextLine());
            System.out.print("   Data de nascimento (dd/MM/yyyy): ");


            clienteDAO.gravar(cliente);

            System.out.println("\n✅ Cliente e endereço vinculados e cadastrados com sucesso!");

        } catch (IOException | JAXBException e) {
            System.out.println(" Erro ao consultar o CEP na API ou processar o XML: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Ocorreu um erro inesperado. Detalhes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (sc != null) sc.close();
            if (em != null && em.isOpen()) em.close();
            EntityManagerUtil.closeEmf();
        }
    }
}
//package br.unipar.devbackend.agendaenderecos;
//
//import br.unipar.devbackend.agendaenderecos.dao.ClienteDAO;
//import br.unipar.devbackend.agendaenderecos.dao.EnderecoDAO;
//import br.unipar.devbackend.agendaenderecos.model.Clientes;
//import br.unipar.devbackend.agendaenderecos.model.Endereco;
//import br.unipar.devbackend.agendaenderecos.service.ViaCepService;
//import br.unipar.devbackend.agendaenderecos.util.EntityManagerUtil;
//import jakarta.persistence.EntityManager;
//import jakarta.xml.bind.JAXBException;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Scanner;
//
//public class MainViaCep {
//
//    public static void main(String[] args) {
//        EntityManager em = EntityManagerUtil.getEm();
//        EnderecoDAO enderecoDAO = new EnderecoDAO(em);
//        ClienteDAO clienteDAO = new ClienteDAO(em);
//        Scanner sc = new Scanner(System.in);
//
//        try {
//            System.out.print("Digite o CEP para buscar o endereço: ");
//            String cep = sc.nextLine();
//
//            Endereco endereco = enderecoDAO.findByCep(cep);
//
//            if (endereco != null) {
//                System.out.println("\n Endereço encontrado no banco de dados:");
//                System.out.println("   Logradouro: " + endereco.getLogradouro());
//                System.out.println("   Bairro: " + endereco.getBairro());
//                System.out.println("   Cidade: " + endereco.getLocalidade());
//                System.out.println("   UF: " + endereco.getUf());
//            } else {
//                System.out.println("\n️ Endereço não encontrado no banco. Consultando ViaCEP...");
//                ViaCepService viaCepService = new ViaCepService();
//                endereco = viaCepService.buscarCep(cep);
//                endereco.setDataCadastro(LocalDateTime.now());
//                System.out.println("   Endereço retornado da API ViaCEP: " + endereco.getLogradouro() + ", " + endereco.getLocalidade());
//            }
//
//            System.out.println("\n➡ Agora, vamos cadastrar o cliente associado a este endereço.");
//            Clientes cliente = new Clientes();
//            System.out.print("   Nome do cliente: ");
//            cliente.setNome(sc.nextLine());
//            System.out.print("   Email do cliente: ");
//            cliente.setEmail(sc.nextLine());
//            System.out.print("   Data de nascimento (dia / mes / ano): ");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
//            cliente.setDataNascimento(LocalDate.parse(sc.nextLine(), formatter));
//            cliente.adicionarEndereco(endereco);
//
//            clienteDAO.gravar(cliente);
//
//            System.out.println("\n Cliente e endereço cadastrados com sucesso!");
//
//        } catch (IOException | JAXBException e) {
//            System.out.println(" Erro ao consultar o CEP na API ou processar o XML: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println(" Ocorreu um erro inesperado: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (sc != null) sc.close();
//            if (em != null && em.isOpen()) em.close();
//            EntityManagerUtil.closeEmf();
//        }
//    }
//}

//package br.unipar.devbackend.agendaenderecos;
//
//import br.unipar.devbackend.agendaenderecos.dao.ClienteDAO;
//import br.unipar.devbackend.agendaenderecos.dao.EnderecoDAO;
//import br.unipar.devbackend.agendaenderecos.model.Clientes;
//import br.unipar.devbackend.agendaenderecos.model.Endereco;
//import br.unipar.devbackend.agendaenderecos.service.ViaCepService;
//import br.unipar.devbackend.agendaenderecos.util.EntityManagerUtil;
//import jakarta.persistence.EntityManager;
//import jakarta.xml.bind.JAXBException;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Scanner;
//
//public class MainViaCep {
//
//    public static void main(String[] args) {
//        EntityManager em = EntityManagerUtil.getEm();
//        EnderecoDAO enderecoDAO = new EnderecoDAO(em);
//        ClienteDAO clienteDAO = new ClienteDAO(em);
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("Digite o CEP: ");
//        String cep = sc.nextLine();
//
//        try {
//            Endereco endereco = enderecoDAO.findByCep(cep);
//
//            if (endereco != null) {
//                System.out.println("✅ Endereço já existe: " + endereco.getLogradouro() + ", " + endereco.getLocalidade());
//            } else {
//                System.out.println("⚠️ Endereço não encontrado no banco. Consultando ViaCEP...");
//
//                ViaCepService viaCepService = new ViaCepService();
//                endereco = viaCepService.buscarCep(cep);
//                endereco.setDataCadastro(LocalDateTime.now());
//
//                enderecoDAO.gravar(endereco);
//                System.out.println("📌 Novo endereço cadastrado com sucesso!");
//            }
//
//            // Cadastro do cliente vinculado
//            Clientes cliente = new Clientes();
//            System.out.print("Nome do cliente: ");
//            cliente.setNome(sc.nextLine());
//            System.out.print("Email do cliente: ");
//            cliente.setEmail(sc.nextLine());
//            System.out.print("Data de nascimento (yyyy-MM-dd): ");
//            cliente.setDataNascimento(LocalDate.parse(sc.nextLine()));
//
//            cliente.adicionarEndereco(endereco);
//            clienteDAO.gravar(cliente);
//
//            System.out.println(" Cliente cadastrado com sucesso!");
//        } catch (IOException | JAXBException e) {
//            System.out.println(" Erro ao consultar CEP: " + e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (em.isOpen()) em.close();
//            EntityManagerUtil.closeEmf();
//        }
//    }
//}


//package br.unipar.devbackend.agendaenderecos;
//
//import br.unipar.devbackend.agendaenderecos.model.Endereco;
//import br.unipar.devbackend.agendaenderecos.service.ViaCepService;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.xml.bind.JAXBException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class MainViaCep {
//
//    public static void main(String[] args) throws IOException, JAXBException {
//        String cep = "85818600";
//
//        ViaCepService viaCep = new ViaCepService();
//        Endereco endereco = viaCep.buscarCep(cep);
//
//        System.out.println("CEP: " + endereco.getCep() +
//                ", Logradouro: " + endereco.getLogradouro() +
//                ", Bairro: " + endereco.getBairro() +
//                ", Localidade: " + endereco.getLocalidade() +
//                "/" + endereco.getUf());
//
//        //buscar um cep qualquer
//        //verificar se esse cep existe no banco de dados
//        //se existir, pede pra adicionar um cleinte para ele
//        //se não existir, grava o novo cep com data e hora da gravação
//    }
//}
