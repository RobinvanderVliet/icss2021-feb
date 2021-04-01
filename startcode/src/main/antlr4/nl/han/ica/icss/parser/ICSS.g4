grammar ICSS;

//LEXER

//If support
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';

//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXEL: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;

//Color value takes precedence over ID identifiers
COLOR: '#' [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f] ([0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f])?;

//Specific identifiers for IDs and CSS classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//Functions
RANDOM: 'random';
RGB: 'rgb';
HSV: 'hsv';

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//Skip comments and whitespace
BLOCK_COMMENT: '/*' .*? ('*/' | EOF) -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
WS: [ \t\r\n]+ -> skip;

//Symbols
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
OPEN_PARENTHESIS: '(';
CLOSE_PARENTHESIS: ')';
SEMICOLON: ';';
QUESTION: '?';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
SPACESHIP: '<=>';
LT: '<';
LTE: '<=';
GT: '>';
GTE: '>=';
EQUAL: '==';
UNEQUAL: '!=';
AND: '&&';
OR: '||';
NOT: '!';
ASSIGNMENT_OPERATOR: ':=';
COMMA: ',';

//PARSER

stylesheet: (stylerule | variableAssignment)* EOF;
stylerule: selector styleblock;

//Selectors
selector: classSelector | idSelector | tagSelector;
classSelector: CLASS_IDENT;
idSelector: ID_IDENT;
tagSelector: LOWER_IDENT;

//Literals and variables
literal: bool | color | percentage | pixel | scalar;
bool: TRUE | FALSE;
color: COLOR;
percentage: PERCENTAGE;
pixel: PIXEL;
scalar: SCALAR;
variableAssignment: variableReference ASSIGNMENT_OPERATOR expression SEMICOLON;
variableReference: CAPITAL_IDENT;
value: literal | variableReference;

//Declarations
styleblock: OPEN_BRACE (ifClause | declaration | variableAssignment)* CLOSE_BRACE;
propertyName: LOWER_IDENT;
declaration: propertyName COLON expression SEMICOLON;

//Clauses
ifClause: IF BOX_BRACKET_OPEN expression BOX_BRACKET_CLOSE styleblock elseClause?;
elseClause: ELSE styleblock;

//Expressions and operations
expression:
    RANDOM OPEN_PARENTHESIS expression COMMA expression CLOSE_PARENTHESIS #random |
    RGB OPEN_PARENTHESIS expression COMMA expression COMMA expression CLOSE_PARENTHESIS #rgb |
    HSV OPEN_PARENTHESIS expression COMMA expression COMMA expression CLOSE_PARENTHESIS #hsv |

    OPEN_PARENTHESIS expression CLOSE_PARENTHESIS #parenthesis |

    NOT expression #negation |
    MIN expression #minus |

    expression MUL expression #multiplication |
    expression PLUS expression #addition |
    expression MIN expression #subtraction |

    expression QUESTION expression COLON expression #conditional |

    expression SPACESHIP expression #spaceship |

    expression LT expression #lessThan |
    expression LTE expression #lessThanOrEqual |
    expression GT expression #greaterThan |
    expression GTE expression #greaterThanOrEqual |

    expression EQUAL expression #equal |
    expression UNEQUAL expression #unequal |

    expression AND expression #and |
    expression OR expression #or |

    value #reference;
