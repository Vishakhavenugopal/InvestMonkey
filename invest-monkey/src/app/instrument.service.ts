import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InstrumentService {
  private apiUrl = 'http://65.2.3.57:8080/client/prices';

  constructor(private http: HttpClient) {}

  getInstruments(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
