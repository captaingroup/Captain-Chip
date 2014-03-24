# TCP server example
import socket
import serial
import time
from random import randint

ser = serial.Serial('/dev/tty.usbmodem1411', 9600)
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(("localhost", 5005))
server_socket.listen(5)

print "TCPServer Waiting for client on port 5004"

while 1:
	client_socket, address = server_socket.accept()
	print "I got a connection from ", address
	i = 1 # i is used for mock data. Actual data comes from Arduino in next line
	arduino_data = ser.readline()
	while 1:
		i = i+1
		data = str(i)
		#data = str(randint(1, 100))
		client_socket.send(data)
		time.sleep(1)