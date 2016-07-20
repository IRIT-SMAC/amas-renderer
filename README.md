# amas-renderer
[![Build Status](https://travis-ci.org/IRIT-SMAC/amas-renderer.svg?branch=master)](https://travis-ci.org/IRIT-SMAC/amas-renderer)

- [Github] (https://github.com/IRIT-SMAC/amas-renderer)
- [Travis] (https://travis-ci.org/IRIT-SMAC/amas-renderer)

Amas-renderer offre la possibilité de créer un fichier de configuration JSON pour amas-factory de façon visuelle. Ainsi, il est possible de définir l'infrastructure, les services et les agents à travers une interface graphique. Les agents sous représentés sous la forme d'un graphe, où un agent est un noeud et un arc est un lien de connaissance. 

Amas-renderer utilise **JavaFX** comme bibliothèque graphique et **GraphStream** pour visualiser et manipuler un graphe. Le patron d'architecture **MVC** est utilisé. Une couche supplémentaire, la couche service, a été utilisée pour décharger les contrôleurs de la logique métier.  Par ailleurs, **Jackson** a été utilisée pour sérialiser et désérialiser les données au format JSON.