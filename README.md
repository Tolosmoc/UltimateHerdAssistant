# UltimateHerdAssistant
Suivi d'animaux Zoo : on a des animaux, des activités, un suivi, éventuellement des stocks. On associe un animal à des activités et on peut suivre un historique. On peut également associé un animal à des nourrissages selon des stocks. L'application permet ainsi de créer : 
- A : des animaux (nom, prénom, type, ...) _ 1-n lié vers B et C
- B : des activités (visite médicale, sortie,...) _ 1-n vers C
- C : un suivi _ lié à A et B
- D : éventuellement des nourrissages n-m avec des stocks.
