
package br.unipar.devbackend.agendaenderecos.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {

    private static EntityManagerFactory emf;

    /**
     * Inicializa o EntityManagerFactory.
     * Deve ser chamado antes de usar getEm().
     */
    public static void init() {
        if (emf == null) {
            try {
                // Nome da unidade de persistência definida no persistence.xml
                emf = Persistence.createEntityManagerFactory("agendaenderecosPU");
                System.out.println("✅ EntityManagerFactory inicializado com sucesso.");
            } catch (Exception ex) {
                System.out.println("❌ Erro ao inicializar EntityManagerFactory: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    /**
     * Retorna um EntityManager.
     * Garante que a factory esteja inicializada.
     */
    public static EntityManager getEm() {
        if (emf == null) {
            init();
        }
        return emf.createEntityManager();
    }

    /**
     * Fecha o EntityManagerFactory
     */
    public static void closeEmf() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("✅ EntityManagerFactory fechado.");
        }
    }

    public static void getEmf() {
    }
}

//package br.unipar.devbackend.agendaenderecos.util;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//public class EntityManagerUtil {
//    private static EntityManagerFactory emf;
//    private static EntityManager em;
//
//    private EntityManagerUtil() {
//    }
//
//    public static EntityManagerFactory getEmf() {
//        if (emf == null) {
//            emf = Persistence
//                    .createEntityManagerFactory(
//                            "agendaEnderecosPU");
//
//            System.out.println("Conexao com o banco de " +
//                    "dados estabelecida");
//
//        }
//        return emf;
//
//    }
//    public static void closeEmf(){
//        if (emf != null && emf.isOpen()){
//            emf.close();
//            System.out.println("Conexão com o banco de "  +
//                    "dados encerrada");
//        }
//    }
//    public static EntityManager getEm(){ //retorna o gerenciador de requisições no banco de dados
//        if (em == null || !em.isOpen()){
//            em = emf.createEntityManager();
//            System.out.println("EntityManager criado. ");
//        }
//        return em;
//    }
//}
