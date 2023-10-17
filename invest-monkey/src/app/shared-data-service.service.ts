import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  holdings: any[] = [];

  constructor() {}
}
