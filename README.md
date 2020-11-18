# GistGitHub App
Repositório para estudo de arquiteturas, tecnologias e componentes do Android.

- Aplicativo que permite o usuário listar gists públicos do GitHub, favoritar para acessar offline e ver detalhes, sendo possível visualizar informações relevantes.



#### Arquitetura
- MVVM baseado em Clean Architecture, pensando na escalabilidade e independência dos módulos/features

###### Árvore de dependências dos módulos do projeto

https://drive.google.com/file/d/1tqZIKatZ5D6b_HFJt_6HJZM3LdMizs8p/view?usp=sharing

- :app -> Módulo android da aplicação
- :gist -> Módulo android da feature gist (listagem, favorito e detalhes)
- :splash -> Módulo android da feature introdutória do app 
- :base -> Módulo android genérico que contém implementações compartilhadas entre as features.
- :core -> Módulo java genérico que contém implementações compartilhadas entre os módulos.
- :network ->  Módulo java para acesso à dados remotos (Retrofit2)
- :database -> Módulo android para acesso à dados locais (Room) 

As injeções de dependências são feitas utilizando Dagger.

###### Organização dos pacotes

https://drive.google.com/file/d/1aPGs6R_qpsN-K7TMEvClotJkHsr6JzfG/view?usp=sharing

Cada módulo de feature terá a seguinte organização:
- {subfeature} -> Neste projeto: listagem, favorito e detalhes
- {subfeature}.domain -> regras de negócio,use cases, mappers e datasource
- {subfeature}.presentation -> activities/fragments, viewmodels, adapters, customviews, etc (android)
- {subfeature}.data -> repositórios
- common -> Arquivos compartilhados entre as subfeatures do módulo. Tem a mesma organização: presentation, domain, data

###### Motivação
Esta arquitetura e organização de pacotes foi utilizada pensando na escalabilidade, manutenibilidade e independências dos módulos. 
- Escalabilidade pois a complexidade em adicionar novas features no projeto é mínima, já que as features e os módulos de acesso à dados são desacoplados uns dos outros.
- Manutenibilidade pois os módulos ficam mais contextualizados, sendo menos custoso encontrar possíveis falhas, realizar refatorações, etc. As camadas ficam mais evidentes também, o que facilita a criação de testes unitários e instrumentados.
- Independências dos módulos pois possibilita o desligamento/remoção de features de maneira fácil, reduz o build-time já que qualquer alteração em um módulo irá compilar apenas os módulos dependentes, etc

#### Android Components

###### Room 
Para armazenar os favoritos localmente e possibilitar o acesso offline.

###### Fragment
Para modularizar as views https://drive.google.com/file/d/1l2bhW6M1_Ansx_J6LBaP2MBvxFWBGmOd/view?usp=sharinge suas responsabilidades e possibilitar o uso do *Navigation.

###### Navigation
Para possilitar e facilitar o controle de navegação entre fragments em uma feature.

###### Lifecycle
Para facilitar o controle de estados e dados da view utilizando LiveData e ViewModel.

###### Databindg
Para facilitar o vínculo de dados à view

###### Paging
Para auxiliar na paginação da lista de gists. Nesta implementação, o PagingSource fica responsável apenas por controlar as páginas (current, next) e se a paginação terminou/nada foi retornado. Ou seja, PagingSource é um intermediador entre o viewmodel e o use case (getlist).

#### Unit Test
Utilizando Mockito

#### Testes instrumentados
O padrão utilizado foi o ScreenRobot pois ele deixa os teste mais expressivos, legíveis e de fácil manutenção. Cada activity ou fragment testado terá seu screen robot que herda um screen robot genérico.
O screen robot genérico terá ações padrões nos testes, como clicar, checar visibilidade, cores, entrar com dados, etc.
Já screen robot do teste, terá ações referentes àquela view. Nele estará concentrado todos os ids  da view, ou seja, caso haja alguma alteração de id, a manutenção será dada em apenas um ponto.

Algumas dependências extras foram adicionadas a fim de testar intenções de navegação e Navigation Component

-----------------------------------------------------------------------------------------------------------
Prints: 
https://drive.google.com/file/d/17rI6hDrv79CihblSpKNmv5iJHyCZ1edV/view?usp=sharing
https://drive.google.com/file/d/1qkuWZp3iFzx2PjW_arSCtgGxj78M0sIX/view?usp=sharing
https://drive.google.com/file/d/1IV554Lg1N3SfmRKlp2ggJsI8zEn3fLpD/view?usp=sharing
https://drive.google.com/file/d/1_JFWaUFIbq3H8-JfEpz5jD6a0UUtJs6h/view?usp=sharing
https://drive.google.com/file/d/1l2bhW6M1_Ansx_J6LBaP2MBvxFWBGmOd/view?usp=sharing

