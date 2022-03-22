all: compile release

compile:
	#echo "Compile in progress"
	mvn clean
	mvn verify
	mvn compile&
	#echo "Compile complete"

# unit-test:
# 	#echo "Testing in progress"&
# 	Shell="java -jar target/output/robot-world-server-1.0.0.jar -s 2"&
# 	mvn test
# 	sh ./killserver.sh
# 	#echo "Testing complete"

release:
	mvn package


