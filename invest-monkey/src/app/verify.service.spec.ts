import { TestBed } from '@angular/core/testing';

import { VerifyService } from './verify.service';

describe('VerifyService', () => {
  let service: VerifyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VerifyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should verify a user with a valid email and password', () => {
    const email = 'abc@gmail.com';
    const password = 'jd123';

    const result = service.verifyUser(email, password);

    expect(result).toBe('USER LOGGED IN');
    expect(service.isUserLoggedIn()).toBe(true);
  });

  it('should return "INVALID CREDENTIALS" for invalid email and password', () => {
    const email = 'invalid@example.com';
    const password = 'invalidpassword';

    const result = service.verifyUser(email, password);

    expect(result).toBe('INVALID CREDENTIALS');
    expect(service.isUserLoggedIn()).toBe(false);
  });

  it('should return "INVALID CREDENTIALS" when email is not found', () => {
    const email = 'nonexistent@example.com';
    const password = 'somepassword';

    const result = service.verifyUser(email, password);

    expect(result).toBe('INVALID CREDENTIALS');
    expect(service.isUserLoggedIn()).toBe(false);
  });
});
