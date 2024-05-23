package model.dao;

import java.util.*;

import java.sql.*;
import model.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

    // TODO: Incluir dependencia de conexao (FEITO)
    private final Connection conexao;

    // TODO: Fazer inversão/injeção de dependencia (FEITO)
    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
        init();
    }

    private void init() {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS fatec;\n";
        String createTable = "CREATE TABLE IF NOT EXISTS fatec.usuarios("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "nome VARCHAR(100) NOT NULL, "
                + "email VARCHAR(100) NOT NULL UNIQUEEsdra, "
                + "senha VARCHAR(100) NOT NULL);";

        try (Statement stm = conexao.createStatement()) {
            stm.execute(createDatabase);
            stm.execute(createTable);
        } catch (Exception e) {
            System.out.println("Erro ao criar a entidade usuarios. Erro: "
                    + e.getLocalizedMessage());
        }
    }

    @Override
    public void salvar(Usuario usuario) throws Exception {
        String sql = "INSERT INTO fatec.usuarios (nome, email, senha) "
                + "values ('%s', '%s', '%s')";

        try (Statement stm = conexao.createStatement()) {
            stm.execute(String.format(sql,
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha()));
        } catch (Exception e) {
            System.out.println("Erro ao criar usuario. Erro: "
                    + e.getLocalizedMessage());
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        // TODO: Buscar usuário em base de dados por e-mail (FEITO)
        String sql = "SELECT * FROM fatec.usuarios WHERE email = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, email);
            try (ResultSet rst = stm.executeQuery()) {
                if (rst.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setNome(rst.getString("nome"));
                    usuario.setEmail(rst.getString("email"));
                    usuario.setSenha(rst.getString("senha"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário por e-mail: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void atualizar(Usuario usuario) {
        // TODO: Atualizar usuário existente em base de dados (FEITO)
        String sql = "UPDATE fatec.usuarios SET nome = ?, senha = ? WHERE email = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getSenha());
            stm.setString(3, usuario.getEmail());
            stm.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @Override
    public void exluir(Integer id) {
        // TODO: Atualizar usuário existente em base de dados (FEITO)
        String sql = "DELETE FROM fatec.usuarios WHERE id = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> buscarTodos() {
        String sql = "SELECT * FROM fatec.usuarios where email = ";
        List<Usuario> usuarios = new ArrayList<>();

        try (Statement stm = conexao.createStatement();
                ResultSet rst = stm.executeQuery(sql)) {
            while (rst.next()) {
                Usuario usuario = new Usuario();
                usuario.setNome(rst.getString("nome"));
                usuario.setEmail(rst.getString("email"));
                usuario.setSenha(rst.getString("senha"));

                usuarios.add(usuario);
            }

            return usuarios;
        } catch (Exception e) {
            System.out.println("Falha ao recuperar usuarios.");
        }

        return usuarios;
    }

}
