lexer grammar Lalex;

PALAVRA_CHAVE: 'algoritmo' | 'declare' | 'fim_algoritmo' | 'leia' | 'escreva'
                | TIPO_DADO | SIMBOLOS | VALOR_LOGICO | COMANDO_CONDICIONAL | COMANDO_REPETICAO
                | SUB_ROTINA | COMANDO_REGISTRO | OPERADOR_LOGICO;


//Fragmentos para palavras-chave e operadores
fragment TIPO_DADO: 'inteiro' | 'real' | 'literal' | 'logico' | 'constante';
fragment OPERADOR_LOGICO: 'nao' | 'e' | 'ou';
fragment VALOR_LOGICO: 'verdadeiro' | 'falso';
fragment COMANDO_CONDICIONAL: 'se' | 'entao' | 'senao' | 'fim_se' | 'caso' | 'seja' | 'fim_caso';
fragment COMANDO_REPETICAO: 'para' | 'ate' | 'faca' | 'fim_para' | 'enquanto' | 'fim_enquanto';
fragment SUB_ROTINA: 'funcao' | 'fim_funcao' | 'retorne' | 'procedimento' | 'fim_procedimento' | 'var';
fragment COMANDO_REGISTRO: 'registro' | 'fim_registro' | 'tipo';

//fragmentos para simbolos e operadores
fragment SIMBOLOS: '<-' | '(' | ')' | '[' | ']' | '.' | '^' | '&' | '..' | ':' | ','
                | OPERADOR_RELACIONAL | OP1 | OP2 | OP3;
fragment OP1: '%' | '-';    // '-' como operador unario
fragment OP2: '*' | '/';
fragment OP3: '+' | '-';    //'-' como operador binario
fragment OPERADOR_RELACIONAL: '=' | '<>' | '>' | '<' | '>=' | '<=';

//Fragmentos para números
fragment ZERO: '0';
fragment NON_ZERO: ('1'..'9');
fragment DIGITO: ('0'..'9');

//Fragmentos para letras
fragment LETRA: ('a'..'z' | 'A'..'Z');

//Regras de Tokens
IDENT: LETRA (LETRA | DIGITO | '_')*;

NUM_INT: ZERO | NON_ZERO DIGITO*;
NUM_REAL: DIGITO+ '.' DIGITO+;

CADEIA: '"' ~('\n' | '"')* '"';

//Tokens ignorados
IGNORADO: (WS | TAB | QUEBRA_LINHA | COMENTARIO) -> skip;
fragment WS: ' ';
fragment TAB: '\t';
fragment QUEBRA_LINHA: '\n';
fragment COMENTARIO: '{ ' ~('\n'|'}')* '}';

//Tokens de Erros
ERRO_CADEIA: '"' ~('\n'|'"')* '\n';
ERRO_COMENTARIO: '{' ~('\n'|'}')* '\n';
NAO_RECONHECIDO: .;

