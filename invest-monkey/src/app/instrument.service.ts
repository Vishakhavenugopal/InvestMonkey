import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InstrumentService {
  private apiUrl = 'https://a721308.roifmr.com/client/prices';

  constructor(private http: HttpClient) {}

  getInstruments(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
