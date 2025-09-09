package br.unipar.devbackend.agendaenderecos.dao;

import br.unipar.devbackend.agendaenderecos.model.Endereco;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EnderecoDAO {

    private final EntityManager em;

    public EnderecoDAO(EntityManager em) {
        this.em = em;
    }

    public Endereco gravar(Endereco endereco) {
        try {
            em.getTransaction().begin();
            em.persist(endereco);
            em.getTransaction().commit();
            return endereco;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.out.println("❌ Erro ao gravar endereço: " + ex.getMessage());
            return null;
        }
    }

    public Endereco editar(Endereco endereco) {
        try {
            em.getTransaction().begin();
            Endereco atualizado = em.merge(endereco);
            em.getTransaction().commit();
            System.out.println("✅ Endereço editado com sucesso");
            return atualizado;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.out.println("❌ Erro ao editar endereço: " + ex.getMessage());
            return null;
        }
    }

    public Endereco findById(long id) {
        return em.find(Endereco.class, id);
    }

    public Endereco findByCep(String cep) {
        List<Endereco> lista = em.createQuery("SELECT t FROM Endereco t WHERE t.cep = :p_cep", Endereco.class)
                .setParameter("p_cep", cep)
                .getResultList();
        return lista.isEmpty() ? null : lista.get(0);
    }

    public List<Endereco> findAll() {
        return em.createQuery("SELECT t FROM Endereco t", Endereco.class)
                .getResultList();
    }

    public boolean delete(Endereco endereco) {
        try {
            em.getTransaction().begin();
            Endereco ref = em.contains(endereco) ? endereco : em.merge(endereco);
            em.remove(ref);
            em.getTransaction().commit();
            System.out.println(" Endereço removido com sucesso!");
            return true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.out.println(" Erro ao remover endereço: " + ex.getMessage());
            return false;
        }
    }
}


//package br.unipar.devbackend.agendaenderecos.dao;
//
//import br.unipar.devbackend.agendaenderecos.model.Endereco;
//import jakarta.persistence.EntityManager;
//
//import java.util.List;
//
//public class EnderecoDAO {
//
//    EntityManager em;
//
//    public EnderecoDAO(EntityManager em){this.em = em;}
//
//    public Endereco gravar(Endereco endereco) {
//        try{
//            em.getTransaction().begin();
//            em.persist(endereco);
//            em.getTransaction().commit();
//            return endereco;
//        } catch (Exception ex) {
//            em.getTransaction().rollback();
//            System.out.println("Algo de errado não deu certo: " + ex.getMessage());
//            return null;
//
//        }finally {
//            if (em.isOpen()){
//                em.close();
//                System.out.println("EntityManager fechado: ");
//            }
//        }
//    }
//
//    public Endereco editar(Endereco endereco){
//        try {
//            em.getTransaction().begin();
//            em.merge(endereco);
//            em.getTransaction().commit();
//            System.out.println("Endereco editado com sucesso" );
//            return endereco;
//        } catch (Exception ex) {
//            em.getTransaction().rollback();
//            System.out.println("Algo de errado não deu certo ao salvar: " + ex.getMessage());
//            return null;
//
//        } finally {
//            if (em.isOpen()){
//                em.close();
//                System.out.println("EntityManager fechado. ");
//            }
//        }
//    }
//
//    public Endereco findById(long id) {
//        return em.find(Endereco.class, id);
//    }
//
//    public List<Endereco> findByCep(String cep) {
//        return em.createQuery ("SELECT t FROM Endereco t WHERE t.cep = :p_cep",
//                Endereco.class)
//                .setParameter("p_cep" , cep).getResultList();
//    }
//    public List<Endereco> findAll(){
//        return em.createQuery("SELECT t FROM Endereco t" , Endereco.class)
//                .getResultList();
//    }
//    public Boolean delete(Endereco endereco) {
//        try{
//            em.getTransaction().begin();
//            em.remove(endereco);
//            em.getTransaction().commit();
//        System.out.println("Endereco removido com sucesso! ");
//        return true;
//        } catch (Exception ex) {
//            em.getTransaction().rollback();
//            System.out.println("Algo de errado não deu certo ao remover! "
//            + ex.getMessage());
//            return false;
//        } finally {
//            if (em.isOpen()){
//                em.close();
//                System.out.println("EntityManager fechado. ");
//            }
//        }
//    }
//
//}
