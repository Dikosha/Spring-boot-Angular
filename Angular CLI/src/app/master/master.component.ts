import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import DateTimeFormat = Intl.DateTimeFormat;
import {AppComponent} from '../app.component';

@Component({
  selector: 'app-master',
  templateUrl: './master.component.html',
  styleUrls: ['./master.component.css']
})

export class MasterComponent implements OnInit {
  isAuthorized = false;
  masterName;
  masterPhone;
  masterID;
  masterServices = [];
  masterComments: Array<{author_name: string, content: string, date: Date}> = [];
  constructor(private tokenStorage: TokenStorageService, private http: HttpClient) {}
      // tslint:disable-next-line:label-position
  model: CommentViewModel = {
          master_id: 0,
          content: '',
          user_id: 0,
          date: null
  };

  ngOnInit(): void {
      console.log(this.model);
      console.log(this.tokenStorage.getUser());
      if (this.tokenStorage.getUser() != null) {
          this.isAuthorized = true;
          this.model.user_id = this.tokenStorage.getUser().id;
      }
      const searchObject = window.location.search;
      const urlParams = new URLSearchParams(searchObject);
      this.masterID = urlParams.get('id');
      const url = 'http://localhost:8080/master/profile';
      this.http.post <any>(url, this.masterID,
          {
              headers: new HttpHeaders({'Content-Type': 'application/json'})
          })
          .subscribe(
              data => {
                  console.log(data);
                  this.model.master_id = Number(this.masterID);
                  this.masterName = data.RESULT.master.master_name;
                  this.masterPhone = data.RESULT.master.master_phone;
                  for (let i = 0; i < data.RESULT.services.length; i++) {
                      this.masterServices[i] = data.RESULT.services[i].service_name;
                  }
                  console.log(this.masterServices);
                  for (let i = 0; i < data.RESULT.master_comments.length; i++) {
                      this.masterComments[i] = {author_name: '', content: '', date: new Date()};
                      this.masterComments[i].author_name = data.RESULT.master_comments[i].comment_author;
                      this.masterComments[i].content = data.RESULT.master_comments[i].comment_content;
                      this.masterComments[i].date = data.RESULT.master_comments[i].comment_date;
                  }
              }
          );
  }

    addComment(): void {
        // @ts-ignore
        this.model.content = document.getElementById('content').value;
        this.model.date = new Date();
        console.log(this.model);
        this.http.post('http://localhost:8080/comment/add', this.model).subscribe(
            res => {
                console.log(res);
                // @ts-ignore
                if (res.STATUS === 200) {
                    location.reload();
                } else {
                    alert('Not Added. Try again.');
                }
            },
            error => {
                alert('Something is wrong');
            }
        );
    }
}
export interface CommentViewModel {
    master_id: number;
    content: string;
    user_id: number;
    date: Date;
}
