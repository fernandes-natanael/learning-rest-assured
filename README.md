## Propose

Hello dear, this repository is designated to post my advances in how to use the framework Rest Assured in Java. This is my first time testing software, and it has been a wonderful experience.

Here I'm trying to do a small documentation to help in the future understand better how to use Rest Assured, and to track my evolution.

## What are you going to find?

You're going to find 2 repository: 

- [**main**](https://github.com/fernandes-natanael/learning-rest-assured): where we have a real project tested. You can find the project at <https://seubarriga.wcaquino.me/>. 
- [**test training**](https://github.com/fernandes-natanael/learning-rest-assured/tree/testes-aprendizagem): where we have some tests that I make before the main project, to learning and understand better how test works.
- [**gh-pages**](): documentation where I post some topics about Testing Software and Rest Assured. 🚩 in construction.

## Test Cases

### Deny Access API Without Token

- GET/contas

### Register An BIlls Account  Successfully

- POST/signin:
  - email; String
  - senha(password); String
- POST/contas:
  - nome(name); String

### Changing Bill Successfully


- PUT/contas/:id:
  - nome(new name); String

### Deny Include Bill With Repeated Name

- POST/contas: throw Error when equals to existent bill.
    - nome(name); String

### Insert Balance Successfully

- POST/transcoes:
  - id; LOng
  - conta_id; Long
  - usuario_id; Long
  - descricao; String
  - envolvido; String
  - tipo(Despesa/Receita); String
  - data_transacao(dd/MM/YYYY); Date
  - data_pagamento(dd/MM/YYYY); Date
  - valor; Float
  - status; Boolean

#### Validate Exception Flows In Insert Balance

### Deny Remove Account With Active Balance

- DELETE/contas/:id:

### Calculate Bills Balance

- GET/saldo

### Remove Balance

- DELETE/transacoes/:id:

## References

Here we have the references to the project 

### Payed

- [Testando API Rest com REST-assured](https://www.udemy.com/course/testando-api-rest-com-rest-assured/): it's in portuguese, even if you don't speak portuguese you can do the course, but it can be more difficult.

### For free

## Observations

Sorry for my portuguese commits, and direct main commits, I'm in construction to be more open source 😊.
