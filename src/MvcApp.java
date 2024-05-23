import java.util.*;
import java.sql.Connection;

import model.Usuario;
import model.dao.ConexaoFactory;
import model.dao.IUsuarioDAO;
import model.dao.UsuarioDAO;
import model.reposirory.*;
import services.UsuarioService;

public class MvcApp {
    public static void main(String[] args) throws Exception {

        /*
         * Este conjunto de instruções inicializaram as dependencias 
         * para o uso do serviço de contatos utilizando o repositório
         * com o SGBD MySQL.
         */
        Connection conexao = ConexaoFactory.getConexao();
        IUsuarioDAO dao = new UsuarioDAO(conexao);
        UsuarioRepository repository = new UsuarioRepositoryMySQLImpl(dao);

        UsuarioService service = new UsuarioService(repository);
        
        /*
         * Utilize as leituras em console se preferir.
         * Scanner in = new Scanner(System.in);
         * System.out.println("Digite o nome: ");
         * String nome = in.nextLine();
         * System.out.println("Digite o e-mail: ");
         * String email = in.nextLine();
         * System.out.println("Digite o senha: ");
         * String senha = in.nextLine();
         */
        

        Usuario u1 = new Usuario(null, "Nome", "email@email.com", "123edc");

         // Chamada do metodo de persistencia
        service.criar(u1);

        Usuario u2 = new Usuario(null, "Nome2", "email2@email.com", "456edc");
        Usuario u3 = new Usuario(null, "Nome3", "email3@email.com", "789edc");
        service.criar(u2);
        service.criar(u3);

        List<Usuario> usuarios = service.obterTodos();
        System.out.println("Usuários cadastrados:");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }

        service.excluir(u1.getId());


        Usuario usuarioEncontrado = service.buscarPorEmail(u2.getEmail());
        System.out.println("Segundo usuário encontrado:");
        System.out.println(usuarioEncontrado);

        usuarios = service.obterTodos();
        System.out.println("Usuários cadastrados após remoção:");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }
} 
