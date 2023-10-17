import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { VerifyService } from './verify.service';
import { Observable } from 'rxjs';
import { Location } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private verifyService: VerifyService,
    private router: Router,
    private location: Location // Inject the Location service
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    // Check if the user is logged in
    if (this.verifyService.isUserLoggedIn()) {
      // Check if the user is trying to navigate back to the login page
      if (state.url === '/login') {
        // Redirect to another route (e.g., home) if the user is already authenticated
        return this.router.createUrlTree(['/home']);
      }
      return true; // Allow access to the route
    } else {
      // Redirect to the login page if the user is not logged in
      return this.router.createUrlTree(['/login']);
    }
  }
}
