import { Component, OnInit } from '@angular/core';
import { VerifyService } from 'src/app/verify.service';
import { Client } from 'src/app/models/client';
@Component({
  selector: 'app-navbar2',
  templateUrl: './navbar2.component.html',
  styleUrls: ['./navbar2.component.css']
})
export class Navbar2Component implements OnInit
{
  currentClient?:Client;
  constructor(private verifyService:VerifyService){

  }
  ngOnInit(): void {
    //this.currentClient=this.verifyService.getCurrentClient();
  }
  
}
