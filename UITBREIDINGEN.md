# Eigen uitbreidingen

* Een variabele heeft altijd een vast type.
* Er kunnen haakjes worden gebruikt in rekensommen. Haakjes gaan boven vermenigvuldigen.
* Er kan gebruik gemaakt worden van de unaire negatie-operator: `!`. Deze kan worden gebruikt om booleans om te draaien, maar ook om kleuren om te draaien.
* Er kan gebruik gemaakt worden van de unaire minus-operator: `-`. Deze kan worden gebruikt om een getal van min naar plus of van plus naar min te veranderen.
* Er kan gebruik gemaakt worden van de volgende binaire booleaanse operators: `<` (kleiner dan), `<=` (kleiner dan of gelijk aan), `>` (groter dan), `>=` (groter dan of gelijk aan), `==` (gelijk aan), `!=` (niet gelijk aan), `&&` (en) en `||` (of).
* Er kan gebruik gemaakt worden van de zogenaamde spaceship-operator: `x <=> y`. De spaceship-operator wordt gebruikt om twee expressies van hetzelfde type te vergelijken. De operator geeft de scalaire waarde `-1`, `0` of `1` terug wanneer `x` respectievelijk kleiner dan, gelijk aan of groter dan `y` is.
* Er kan gebruik worden gemaakt van de conditionele ternaire operator: `x ? y : z`. Als `x` waar is, dan krijg je `y` terug, anders krijg je `z` terug.
* Booleans kunnen net als kleuren niet worden gebruikt in rekenoperaties.
* Een regel commentaar kan geplaatst worden met `//`.
* Een blok commentaar kan geplaatst worden tussen `/*` en `*/`.
* Naast `#ABCDEF` kan een kleur ook worden aangemaakt met `#ABC` (= `#AABBCC`), `rgb(x, y, z)` (= rood, groen, blauw) en `hsv(x, y, z)` (= kleurtoon, verzadiging, waarde).
* Met `random(x, y)` kan een willekeurige waarde worden gegenereerd tussen de twee parameters. De parameters moeten nummers zijn van hetzelfde type. De willekeurige waarde wordt pas gegenereerd in de stap transformatie.
* De lijst van toegestane CSS-eigenschappen is eenvoudig uit te breiden in de constructor van de checker.
