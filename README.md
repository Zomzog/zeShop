[![Build Status](https://travis-ci.org/Zomzog/zeShop.svg?branch=master)](https://travis-ci.org/Zomzog/zeShop)
[![Coverage Status](https://coveralls.io/repos/github/Zomzog/zeShop/badge.svg?branch=master)](https://coveralls.io/github/Zomzog/zeShop?branch=master)
[![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg)](https://github.com/Zomzog/zeShop/blob/master/LICENSE)

zeShop
===================
ZeShop is a free, open source e-commerce solution. 
The solution is build with micro-sevices architecture based on spring-cloud framework.

----------


Quick start
-------------
- cd docker
- docker-compose up

Getting Started (DEV Front)
-------------
- ./mvnw clean install
- docker-compose -f docker-compose.yml -f docker-compose.dev.yml up -d

Getting Started (DEV Back)
-------------
###Server order :

- Configuration server
- Eureka server
- Gateway server
- Auth server
- Product server
- Cart server