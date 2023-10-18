const request = require('request');
const app = require('../app');  
const baseUrl = 'http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:3004'; 
var http = require('http');

describe('Express API', () => {
    let server;
    
    beforeAll(() => {
        server = http.createServer(app);
        server.listen(3004);
    });
    afterAll((done) => {
        
      server.close(done); 
    });

  describe('POST /client/login', () => {
    it('should log in a client', (done) => {
      const loginRequest = { "email":"vishakhav888@gmail.com",
      "password":"vish123" };
      request.post(`${baseUrl}/client/login`, { json: loginRequest }, (err, res, body) => {
        if (err) {
          
          return done(err);
        }
        expect(res.statusCode).toBe(200);
        expect(body).toBeDefined();
        
        done();
      });
    });
  });

  describe('Invalid Endpoint', () => {
    it('should return 404 for invalid endpoint', (done) => {
      request.get(`${baseUrl}/invalidEndpoint`, (err, res, body) => {
        if (err) {
          
          return done(err);
        }
        expect(res.statusCode).toBe(404);
        done();
      });
    });
  });

  describe('GET /client/roboAdvisor/336329250', () => {
    it('should log in a client', (done) => {
      
      request.get(`${baseUrl}/client/roboAdvisor/336329250`,  (err, res, body) => {
        if (err) {
          
          return done(err);
        }
        expect(res.statusCode).toBe(200);
        expect(body).toBeDefined();
        
        done();
      });
    });
  });

  describe('GET /client/pref/336329250', () => {
    it('should log in a client', (done) => {
      
      request.get(`${baseUrl}/client/pref/336329250`,  (err, res, body) => {
        if (err) {
          
          return done(err);
        }
        expect(res.statusCode).toBe(200);
        expect(body).toBeDefined();
        
        done();
      });
    });
  });

  describe('GET /client/tradeHistory/336329250', () => {
    it('should log in a client', (done) => {
      
      request.get(`${baseUrl}/client/tradeHistory/336329250`,  (err, res, body) => {
        if (err) {
          
          return done(err);
        }
        expect(res.statusCode).toBe(200);
        expect(body).toBeDefined();
        
        done();
      });
    });
  });

  describe('GET /client/holdings/336329250', () => {
    it('should log in a client', (done) => {
      
      request.get(`${baseUrl}/client/holdings/336329250`,  (err, res, body) => {
        if (err) {
          
          return done(err);
        }
        expect(res.statusCode).toBe(200);
        expect(body).toBeDefined();
        
        done();
      });
    });
  });
 

});





