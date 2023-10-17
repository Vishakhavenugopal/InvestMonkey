import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { VerifyService } from './verify.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'invest-monkey';

  constructor(private location: Location,private verifyService:VerifyService) {}

  ngOnInit() {
    this.location.subscribe(() => {
      // Check if the user is logged in
      if (this.verifyService.isUserLoggedIn()) {
        // Check the current URL
        const currentUrl = this.location.path();
        if (currentUrl === '/login') {
          window.history.forward();
        }
      }
    });
  }

  private isUserLoggedIn(): boolean {
    
    return true; // Replace with your actual authentication check
  }
}
