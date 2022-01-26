ls -1 *0?_bootdb* | sort | xargs cat > setup.sql 
mysql --host=127.0.0.1 --port=3306 -u root --password="secret" < setup.sql 
