CREATE USER 'hotel_management_app'@'localhost' 
IDENTIFIED WITH mysql_native_password BY 'hotel_management_app';
GRANT ALL PRIVILEGES ON * . * TO 'hotel_management_app'@'localhost';