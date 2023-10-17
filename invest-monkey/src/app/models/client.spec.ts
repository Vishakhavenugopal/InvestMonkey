import { Client } from './client';

describe('Client', () => {
  it('should create an instance', () => {
    expect(new Client('Jitin','A','9090909090','AA1234','abc@gmail.com','20012708','India','57001','jd123',{type: "aadhaar",value: "123412341234"})).toBeTruthy();
  });
});
