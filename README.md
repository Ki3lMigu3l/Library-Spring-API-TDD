# Library API Restful

A Library API Restful é uma aplicação desenvolvida para gerenciar uma biblioteca de livros, permitindo operações como cadastro, consulta, atualização e exclusão de livros através de uma API RESTful. A API segue os princípios do REST para fornecer uma interface limpa e eficiente para integração com outros sistemas ou aplicações frontend.

<br>

### Metodologia Utilizada

Test-Driven Development (TDD)
O projeto foi desenvolvido seguindo a metodologia de Desenvolvimento Orientado por Testes (TDD), garantindo que todos os recursos da API fossem implementados de maneira incremental e testados desde o início. Para cada funcionalidade, foram criados testes unitários que asseguram a confiabilidade e consistência das operações.

<div align="center">
    <h3 align="center">Principais ferramentas e tecnologias utilizadas</h3>
        <img width="50" src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png" alt="Java" title="Java"/>
          <img width="50" src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png" alt="Spring Boot" title="Spring Boot"/>
        <img width="50" src="https://user-images.githubusercontent.com/25181517/192108890-200809d1-439c-4e23-90d3-b090cf9a4eea.png" alt="IntelliJ" title="IntelliJ"/>
        <img width="50" src="https://user-images.githubusercontent.com/25181517/192109061-e138ca71-337c-4019-8d42-4792fdaa7128.png" alt="Postman" title="Postman"/>
        <img width="50" src="https://user-images.githubusercontent.com/25181517/183892181-ad32b69e-3603-418c-b8e7-99e976c2a784.png" alt="Mockito" title="Mockito"/>
        <img width="50" src="https://user-images.githubusercontent.com/25181517/190229463-87fa862f-ccf0-48da-8023-940d287df610.png" alt="Lombok" title="Lombok"/>
</div>

### Como executar? 

  1. Clone o repositório
  2. Compile o projeto com o Maven
  3. Execute os testes
  4. Inicie a aplicação

<br>

<div align="center">
  <img src="https://github.com/Ki3lMigu3l/Library-Spring-API-TDD/blob/main/readme/readme-init.png" alt="readme-init.png" width="862" height="499"/>
</div>

### Spring Boot Actuator
O Spring Boot Actuator é integrado à Library API para fornecer uma série de endpoints que permitem monitorar e gerenciar a aplicação em tempo real. Com ele, você pode acessar informações sobre a saúde da aplicação, métricas de desempenho, status de logs e configurações de ambiente. Os endpoints do Actuator ajudam na detecção de problemas e na análise do desempenho da API.

<br>

### Spring Boot Admin
A integração com o Spring Boot Admin permite que a Library API seja registrada em um painel de administração, onde você pode monitorar a aplicação de forma centralizada. O Spring Boot Admin fornece uma interface web intuitiva, onde você pode visualizar informações detalhadas sobre a saúde, métricas, logs e o estado geral da aplicação. Com isso, é possível gerenciar múltiplas instâncias da Library API e garantir que a aplicação esteja funcionando corretamente.

<div align="center">
  <img src="https://github.com/Ki3lMigu3l/Library-Spring-API-TDD/blob/main/readme/Spring-Boot-Admin.png" alt="readme-init.png" width="951" height="457"/>
</div>

<br>

### Documentação com Swagger
Swagger é um conjunto de ferramentas de software open-source que ajuda a projetar, construir, documentar e consumir APIs RESTful. 

<div align="center">
  <img src="https://github.com/Ki3lMigu3l/Library-Spring-API-TDD/blob/main/readme/swagger-ui-doc.png" alt="readme-init.png" width="902" height="466"/>
</div>

<br>

## Conclusão

A Library API Restful é uma solução de backend robusta para o gerenciamento de bibliotecas, permitindo operações fundamentais como cadastro, consulta, atualização e exclusão de livros por meio de uma interface RESTful. Com a aplicação da metodologia Test-Driven Development (TDD), asseguramos a confiabilidade e a qualidade do código desde as fases iniciais do desenvolvimento, garantindo que cada funcionalidade seja testada e validada.

A integração do Spring Boot Actuator possibilita o monitoramento em tempo real da saúde da aplicação, oferecendo métricas e logs que são essenciais para a manutenção e detecção de problemas. A utilização do Spring Boot Admin facilita a gestão de múltiplas instâncias da Library API, permitindo uma visualização centralizada e intuitiva das informações da aplicação.

Além disso, a documentação gerada pelo Swagger torna a API acessível para desenvolvedores e integradores, proporcionando um entendimento claro de suas funcionalidades e como utilizá-las. Com essas características, a Library API Restful se destaca como uma base sólida para o gerenciamento de bibliotecas, preparada para atender às demandas atuais e futuras de forma eficiente.
