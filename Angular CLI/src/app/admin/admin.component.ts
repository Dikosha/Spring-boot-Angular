import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  isAdmin = false;

  constructor(private tokenStorage: TokenStorageService, private http: HttpClient) { }

  ngOnInit(): void {
    console.log(this.tokenStorage.getUser());
    // if (this.tokenStorage.getUser() != null){
    //   for (let i = 0; i < this.tokenStorage.getUser().roles.length; i++) {
    //     if (this.tokenStorage.getUser().roles[i] === 'ROLE_ADMIN') {
    //       this.isAdmin = true;
    //     }
    //   }
    // }
    // if(this.isAdmin == false){
    //   window.location.href = 'login';
    // }                                    else
    const url = 'http://localhost:8080/admin/getAllUsers';
    this.http.post <any>(url, {
    }, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
        .subscribe(
            data => {
              console.log(data);
            }
        );

  }

}
