import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import DateTimeFormat = Intl.DateTimeFormat;

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
  masterComments: Array<{author_name: string, content: string}> = [];
  constructor(private tokenStorage: TokenStorageService, private http: HttpClient) {}
      // tslint:disable-next-line:label-position
  model: CommentViewModel = {
          master_id: 0,
          content: '',
          user_id: 0
  };

  ngOnInit(): void {
      console.log(this.tokenStorage.getUser());
      if (this.tokenStorage.getUser() != null) {
          this.isAuthorized = true;
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
                  this.model.user_id = this.tokenStorage.getUser().id;
                  this.model.master_id = Number(this.masterID);
                  this.masterName = data.RESULT.master.master_name;
                  this.masterPhone = data.RESULT.master.master_phone;
                  for (let i = 0; i < data.RESULT.services.length; i++) {
                      this.masterServices[i] = data.RESULT.services[i].service_name;
                  }
                  console.log(this.masterServices);
                  for (let i = 0; i < data.RESULT.master_comments.length; i++) {
                      this.masterComments[i] = {author_name: '', content: ''};
                      this.masterComments[i].author_name = data.RESULT.master_comments[i].comment_author;
                      this.masterComments[i].content = data.RESULT.master_comments[i].comment_content;
                  }
              }
          );

      /*this.addComm = (document.getElementById('save-button').addEventListener('click', () => {
          this.commentContent = (document.getElementById('content') as HTMLInputElement).value;
      }) as unknown as HTMLInputElement);
      this.http.post <any>(urlAddComment,
          {
              master_id: masterID,
              content: this.commentContent,
              user_id: this.tokenStorage.getUser(),
              date: new Date()
          },
          {
              headers: new HttpHeaders({'Content-Type': 'application/json'})
          }
      );*/
  }

    addComment(): void {
        // @ts-ignore
        this.model.content = document.getElementById('content').value;
        console.log(this.model);
        // this.model.content = (document.getElementById('content'));
      /*this.http.post('http://localhost:8080/comment/add', this.model).subscribe(
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
        );*/
    }
}
export interface CommentViewModel {
    master_id: number;
    content: string;
    user_id: number;
}
