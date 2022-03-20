# Map generator - transform your design into json

### This program will parse your image and translate it into a json that can be consumed by your game

Steps:  
- extend ```AbstractImageParser```
- define your own rules by implementing the abstract methods

You already have a functional example: ```MyMapParser```. You can play with it using the tests or tha main method.

You can transform this map:
![map](https://i.imgur.com/kdDDlLb.png)

Into json like: 
```
[
  {
    "rowIndex": 0,
    "columnIndex": 0,
    "type": 1
  },
  {
    "rowIndex": 0,
    "columnIndex": 1,
    "type": 4
  },
  {
    "rowIndex": 0,
    "columnIndex": 2,
    "type": 3
  },
  ...
  ]
```