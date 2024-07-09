FROM node:18-alpine
WORKDIR /energy-consumption-simulator/
COPY public/ /energy-consumption-simulator/public
COPY src/ /energy-consumption-simulator/src
COPY package.json /energy-consumption-simulator/
RUN npm install
CMD ["npm", "start"]