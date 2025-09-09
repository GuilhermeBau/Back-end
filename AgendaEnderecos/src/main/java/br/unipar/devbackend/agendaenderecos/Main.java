package br.unipar.devbackend.agendaenderecos;

import br.unipar.devbackend.agendaenderecos.dao.ClienteDAO;
import br.unipar.devbackend.agendaenderecos.dao.EnderecoDAO;
import br.unipar.devbackend.agendaenderecos.model.Clientes;
import br.unipar.devbackend.agendaenderecos.model.Endereco;
import br.unipar.devbackend.agendaenderecos.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jdk.swing.interop.SwingInterOpUtils;
import org.w3c.dom.Entity;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        EntityManagerUtil.getEmf();
        ClienteDAO dao = new ClienteDAO(EntityManagerUtil.getEm());
        List<Clientes> clientes = dao.findByNome("guilherme bau");

        for (Clientes end : clientes) {
            System.out.println("Cliente: " + end.getNome() +
                    ", Email: " + end.getEmail() +
                    ", Nascimento: " + end.getDataNascimento() + "/");
        }
        EntityManagerUtil.closeEmf();
    }
}
/*public class Main {
//    public static void main(String[] args) {
//        EntityManagerUtil.getEmf();
//        EnderecoDAO dao = new EnderecoDAO(EntityManagerUtil.getEm());
//        List<Endereco> enderecos = dao.findByCep("guilherme bau");
//
//        for (Endereco end : enderecos) {
//            System.out.println("CEP: " + end.getCep() +
//                    ", Logradouro: " + end.getLogradouro() +
//                    ",Bairro: " + end.getBairro() +
//                    ", Localidade: " + end.getLocalidade() + "/" + end.getUf());
//        }
//        EntityManagerUtil.closeEmf();
//    }
//}*/


//        Endereco endereco = new Endereco();
//        endereco.setCep("85818200");
//        endereco.setLogradouro("Rua chico");
//        endereco.setBairro("coqueiral");
//        endereco.setComplemento("casa");
//        endereco.setLocalidade("Cascavel");
//        endereco.setUf("PR");
//
//        EnderecoDAO dao = new EnderecoDAO(EntityManagerUtil.getEm());
//        dao.gravar(endereco);
//
//        EntityManagerUtil.closeEmf();
//

//        EntityManagerUtil.getEmf();
//
//        EntityManager em = EntityManagerUtil.getEm();
////        Clientes clientes = em.find(Clientes.class, 1);
////
////        try{
////            em.getTransaction().begin();
////            em.remove(clientes);
////            em.getTransaction().commit();
////        } catch (Exception ex){
////            em.getTransaction().rollback();
////            System.out.println("Algo de errado não deu certo: " + ex.getMessage());
////        } finally {
////            if (em.isOpen()){
////                em.close();
////                System.out.println("EntityManager fechado. ");
////            }
////            System.out.println("Cliente removido: " + clientes.getNome());
////        }
////
////        EntityManagerUtil.closeEmf();
////    }
////        EntityManagerUtil.getEmf();
////
////        EntityManager em = EntityManagerUtil.getEm();
////        Clientes clientes = em.find(Clientes.class, 1);
////
////        System.out.println("Email anterior do " + clientes.getNome() + ":" + clientes.getEmail());
////
////        clientes.setEmail("guilherme123@gmail.com"); //edita o e-mail
////
////        try {
////            em.getTransaction().begin(); //bloco de transação
////            em.merge(clientes); //merge = update !=persist = insert
////        } catch (Exception ex){
////            em.getTransaction().rollback();
////            System.out.println("Algo de errado não deu certo:" + ex.getMessage());
////        } finally {
////            if (em.isOpen()){
////                em.close();
////                System.out.println("EntityManager fechado.");
////            }
////            System.out.println("Email novo do " + clientes.getNome() + ":" + clientes.getEmail());
////        }
////
////    }
//}

//    public static void main(String[] args) {
//        EntityManagerUtil.getEmf();
//
//        EntityManager em = EntityManagerUtil.getEm();
//        List<Clientes> clientes = new ArrayList<>();
//        clientes = em.createQuery("SELECT t from Clientes t", Clientes.class).getResultList();
//
//        for (Clientes cli : clientes ) {
//        System.out.println(cli.getId() + "-" + cli.getNome());
//        }
//    }

//
//    public static void main(String[] args) {
//        EntityManagerUtil.getEmf();
//
//        EntityManager em = EntityManagerUtil.getEm();
//        Clientes clientes = em.find(Clientes.class, 1);
//
//        System.out.println("Cliente: " + clientes.getNome() +
//                "Email: " + clientes.getEmail() +
//                "Data de nascimento: " + clientes.getDataNascimento());
//
//        System.out.println("Enderecos do cliente " + clientes.getNome());
//
//        for (Endereco end : clientes.getEnderecos()) {
//            System.out.println("CEP: " + end.getCep() +
//                    "Logradouro: " + end.getLogradouro() +
//                    "Bairro: " + end.getLocalidade() +
//                    "Localidade: " + end.getLocalidade() + "/" + end.getUf());
//        }
//    }
////    public static void main(String[] args) {
////        EntityManagerUtil.getEmf();
////
////        Clientes clientes = new Clientes();
////        clientes.setNome("gui");
////        clientes.setEmail("guilherme@gmail.com");
////        clientes.setDataNascimento(new Date("1990/01/01"));
////
////        Endereco endereco = new Endereco();
////        endereco.setCep("85818600");
////        endereco.setLogradouro("Rua francisco marchewicz");
////        endereco.setBairro("Colmeia");
////        endereco.setLocalidade("CVEL");
////        endereco.setUf("PR");
////        endereco.setClientes(clientes);
////
////        clientes.getEnderecos().add(endereco);
////
////        EntityManager em = EntityManagerUtil.getEm();
////        try {
////            em.getTransaction().begin();
////            em.persist(clientes);
////            em.getTransaction().commit();
////        } catch (Exception ex) {
////            if (em.isOpen()) {
////                em.close();
////                System.out.println("EntityManager fechado. ");
////            }
////            EntityManagerUtil.closeEmf();
////
////
////        }
////        //primeiro contato com o entitymanager persistimos um endereco
//////    public static void main(String[] args) {
//////        EntityManagerUtil.getEmf();
//////        System.out.println("Hello World");
//////
//////        Endereco endereco = new Endereco();
//////        endereco.setCep("85818600");
//////        endereco.setLogradouro("Rua Francisco");
//////        endereco.setComplemento("casa");
//////        endereco.setBairro("Cataratas");
//////        endereco.setLocalidade("Cascavel");
//////        endereco.setUf("PR");
//////
//////        EntityManager em = EntityManagerUtil.getEm();
//////        try{
//////            em.getTransaction().begin();
//////            em.persist(endereco);
//////            em.getTransaction().commit();
//////        } catch (Exception ex) {
//////            System.out.println("Erro ao persistir o endereço: " + ex.getMessage());
//////        }
//////        finally {
//////            if (em.isOpen()) {
//////                em.close();
//////                System.out.println("EntityManager fechado. ");
//////            }
//////            EntityManagerUtil.closeEmf();
//////        }
//////    }
////    }
//}
