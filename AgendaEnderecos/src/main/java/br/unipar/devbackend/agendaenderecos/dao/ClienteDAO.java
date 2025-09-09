package br.unipar.devbackend.agendaenderecos.dao;

import br.unipar.devbackend.agendaenderecos.model.Clientes;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteDAO {

    private final EntityManager em;

    public ClienteDAO(EntityManager em) {
        this.em = em;
    }

    public Clientes gravar(Clientes cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            System.out.println("✅ Cliente salvo com sucesso!");
            return cliente;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.out.println("❌ Erro ao salvar cliente: " + ex.getMessage());
            return null;
        }
    }

    public Clientes editar(Clientes cliente) {
        try {
            em.getTransaction().begin();
            Clientes atualizado = em.merge(cliente);
            em.getTransaction().commit();
            System.out.println("✅ Cliente editado com sucesso!");
            return atualizado;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.out.println("❌ Erro ao editar cliente: " + ex.getMessage());
            return null;
        }
    }

    public Clientes findById(long id) {
        return em.find(Clientes.class, id);
    }

    public List<Clientes> findByNome(String nome) {
        return em.createQuery("SELECT c FROM Clientes c WHERE c.nome = :p_nome", Clientes.class)
                .setParameter("p_nome", nome)
                .getResultList();
    }

    public List<Clientes> findAll() {
        return em.createQuery("SELECT c FROM Clientes c", Clientes.class)
                .getResultList();
    }

    public boolean delete(Clientes cliente) {
        try {
            em.getTransaction().begin();
            Clientes ref = em.contains(cliente) ? cliente : em.merge(cliente);
            em.remove(ref);
            em.getTransaction().commit();
            System.out.println("🗑 Cliente removido com sucesso!");
            return true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.out.println(" Erro ao remover cliente: " + ex.getMessage());
            return false;
        }
    }
}

//package br.unipar.devbackend.agendaenderecos.dao;
//
//import br.unipar.devbackend.agendaenderecos.model.Clientes;
//import br.unipar.devbackend.agendaenderecos.model.Endereco;
//import jakarta.persistence.EntityManager;
//
//import java.util.List;
//
//public class ClienteDAO {
//
//    EntityManager em;
//
//    public ClienteDAO(EntityManager em) {
//        this.em = em;
//    }
//
//    public Clientes gravar(Clientes clientes) {
//        try {
//            em.getTransaction().begin();
//            em.persist(clientes);
//            em.getTransaction().commit();
//            return clientes;
//        } catch (Exception ex) {
//            em.getTransaction().rollback();
//            System.out.println("Algo de errado não deu certo: " + ex.getMessage());
//            return null;
//
//        } finally {
//            if (em.isOpen()) {
//                em.close();
//                System.out.println("EntityManager fechado: ");
//            }
//        }
//    }
//
//    public Clientes editar(Clientes clientes) {
//        try {
//            em.getTransaction().begin();
//            em.merge(clientes);
//            em.getTransaction().commit();
//            System.out.println("Cliente editado com sucesso");
//            return clientes;
//        } catch (Exception ex) {
//            em.getTransaction().rollback();
//            System.out.println("Algo de errado não deu certo ao salvar: " + ex.getMessage());
//            return null;
//
//        } finally {
//            if (em.isOpen()) {
//                em.close();
//                System.out.println("EntityManager fechado. ");
//            }
//        }
//    }
//
//    public Clientes findById(long id) {
//        return em.find(Clientes.class, id);
//    }
//
//    public List<Clientes> findByNome(String nome) {
//        return em.createQuery("SELECT t FROM Clientes t WHERE t.nome = :p_cliente",
//                        Clientes.class)
//                .setParameter("p_cliente", nome).getResultList();
//    }
//
//    public List<Clientes> findAll() {
//        return em.createQuery("SELECT t FROM Endereco t", Clientes.class)
//                .getResultList();
//    }
//
//    public Boolean delete(Clientes clientes) {
//        try {
//            em.getTransaction().begin();
//            em.remove(clientes);
//            em.getTransaction().commit();
//            System.out.println("Cliente removido com sucesso! ");
//            return true;
//        } catch (Exception ex) {
//            em.getTransaction().rollback();
//            System.out.println("Algo de errado não deu certo ao remover! "
//                    + ex.getMessage());
//            return false;
//        } finally {
//            if (em.isOpen()) {
//                em.close();
//                System.out.println("EntityManager fechado. ");
//            }
//        }
//    }
//}