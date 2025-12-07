package com.lixo.gerenciamento;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.Caminhao;
import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.entity.Rua;
import com.lixo.gerenciamento.model.entity.User;
import com.lixo.gerenciamento.model.enums.TipoResiduo;
import com.lixo.gerenciamento.model.enums.UserRoles;
import com.lixo.gerenciamento.repository.BairroRepository;
import com.lixo.gerenciamento.repository.CaminhaoRepository;
import com.lixo.gerenciamento.repository.PontoColetaRepository;
import com.lixo.gerenciamento.repository.RotaRepository;
import com.lixo.gerenciamento.repository.RuaRepository;
import com.lixo.gerenciamento.repository.UserRepository;

@SpringBootApplication
public class GerenciamentoGreenLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoGreenLogApplication.class, args);
    }

    @Bean
    CommandLineRunner run(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            BairroRepository bairroRepository,
            CaminhaoRepository caminhaoRepository,
            PontoColetaRepository pontoColetaRepository,
            RuaRepository ruaRepository,
            RotaRepository rotaRepository) {
        return args -> {

            // 1. Inicializar Usuário Admin
            if (userRepository.findByUsername("admin").isEmpty()) {
                String encodedPassword = passwordEncoder.encode("password123");
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encodedPassword);
                admin.addRole(UserRoles.ADMIN);
                userRepository.save(admin);
                System.out.println("User 'admin' created with password 'password123'");
            }

            // Verifica se já existem dados para não duplicar a cada reinício
            if (bairroRepository.count() == 0) {
                System.out.println("Inicializando dados de Goiânia...");

                // 2. Criar Bairros (Vértices do Grafo)
                Bairro bueno = new Bairro(null, "Setor Bueno");
                Bairro oeste = new Bairro(null, "Setor Oeste");
                Bairro jardimGoias = new Bairro(null, "Jardim Goiás");
                Bairro centro = new Bairro(null, "Setor Central");
                Bairro marista = new Bairro(null, "Setor Marista");

                bairroRepository.saveAll(Arrays.asList(bueno, oeste, jardimGoias, centro, marista));

                // 3. Criar Caminhões com Placas MERCOSUL (Padrão AAA1A11)
                Caminhao c1 = new Caminhao();
                c1.setPlaca("GYN1A34");
                c1.setNomeMotorista("Seu Zé da Coleta");
                c1.setCapacidade(12000.0);
                c1.setTipoResiduos(List.of(TipoResiduo.ORGANICO, TipoResiduo.PLASTICO));
                c1.setChaveModular(100L); 

                Caminhao c2 = new Caminhao();
                c2.setPlaca("GOI9B76");
                c2.setNomeMotorista("Maria Transportadora");
                c2.setCapacidade(8000.0);
                c2.setTipoResiduos(List.of(TipoResiduo.VIDRO, TipoResiduo.METAL, TipoResiduo.PAPEL));
                c2.setChaveModular(200L);

                caminhaoRepository.saveAll(Arrays.asList(c1, c2));

                // 4. Criar Pontos de Coleta
                PontoColeta pc1 = PontoColeta.builder()
                        .nome("Parque Vaca Brava")
                        .bairro(bueno)
                        .endereco("Av. T-10, S/N - Setor Bueno")
                        .nomeResponsavel("Adm. do Parque")
                        .email("vacabrava@goiania.gov.br")
                        .telefone("(62) 3524-1000")
                        .tiposDeResiduo(List.of(TipoResiduo.PLASTICO, TipoResiduo.METAL))
                        .horarioFuncionamento("08:00 - 18:00")
                        .build();

                PontoColeta pc2 = PontoColeta.builder()
                        .nome("Parque Flamboyant")
                        .bairro(jardimGoias)
                        .endereco("Rua 15, S/N - Jardim Goiás")
                        .nomeResponsavel("Gestão Flamboyant")
                        .email("flamboyant@goiania.gov.br")
                        .telefone("(62) 3524-2000")
                        .tiposDeResiduo(List.of(TipoResiduo.VIDRO, TipoResiduo.ORGANICO))
                        .horarioFuncionamento("06:00 - 22:00")
                        .build();

                PontoColeta pc3 = PontoColeta.builder()
                        .nome("Praça Cívica")
                        .bairro(centro)
                        .endereco("Praça Dr. Pedro Ludovico Teixeira - Centro")
                        .nomeResponsavel("Gov. Estado")
                        .email("pracacivica@goias.gov.br")
                        .telefone("(62) 3200-0000")
                        .tiposDeResiduo(List.of(TipoResiduo.PAPEL))
                        .horarioFuncionamento("24h")
                        .build();

                pontoColetaRepository.saveAll(Arrays.asList(pc1, pc2, pc3));

                // 5. Criar Ruas (Arestas/Conexões entre Bairros)
                Rua r1 = Rua.builder().origem(centro).destino(oeste).distancia(2.5).build(); // Av. Assis Chateaubriand
                Rua r2 = Rua.builder().origem(oeste).destino(bueno).distancia(3.0).build(); // Av. 85
                Rua r3 = Rua.builder().origem(bueno).destino(marista).distancia(1.5).build(); // Av. Ricardo Paranhos
                Rua r4 = Rua.builder().origem(marista).destino(jardimGoias).distancia(4.0).build(); // Av. 136
                Rua r5 = Rua.builder().origem(jardimGoias).destino(centro).distancia(5.5).build(); // Marginal Botafogo (volta)
                Rua r6 = Rua.builder().origem(bueno).destino(jardimGoias).distancia(3.5).build(); // Av. T-63

                ruaRepository.saveAll(Arrays.asList(r1, r2, r3, r4, r5, r6));

                System.out.println("Dados iniciais de Goiânia carregados com sucesso!");
            }
        };
    }
}