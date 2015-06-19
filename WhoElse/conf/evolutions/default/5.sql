# --- !Ups

INSERT INTO `Item` (id,user_id,description,dress_size,color,price_per_day,material,brand,dress_type)
  VALUES
    (1,2,'I love to wear this dress on nice summer nights - its just awesome!',68,'Blue',15,'Silk','D&G','Summer'),
    (2,1,'Great for a evening in a nice bar. Looks just astonishing.',70,'Black',25,'Silk','Armani','Evening'),
    (3,2,'Best dress ever - i just love it.',68,'Black',16,'Cotton','Calvin Klein','Summer'),
    (4,2,'Looks great with some light jewellery.',66,'Red',18,'Silk','Other','Business Dress'),
    (5,3,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eu.',70,'White',12,'Cotton','Gucci','Summer'),
    (6,1,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque in.',72,'Red',21,'Synthetic','Other','Winter'),
    (7,1,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus a lacus id libero venenatis.',72,'Submarine',9,'Fabric','Other','Winter'),
    (8,16,'Lorem ipsum dolor sit amet, consectetur adipiscing.',70,'Black',19,'Synthetic','Other','Winter'),
    (9,8,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In.',70,'Yello',16,'Fabric','Ralph Lauren','Winter'),
    (10,4,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In.',68,'Different Colors',44,'Wool','Versace','Winter');

# --- !Downs

DELETE TABLE Item