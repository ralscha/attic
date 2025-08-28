import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-log-photo',
  templateUrl: './photo.page.html',
  styleUrls: ['./photo.page.scss'],
})
export class LogPhotoPage implements OnInit {
  @ViewChild('fileSelector') fileInput: ElementRef;

  private logIdString!: string;

  constructor(private readonly httpClient: HttpClient,
              private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.logIdString = this.route.snapshot.paramMap.get('id');
  }

  clickFileSelector(): void {
    this.fileInput.nativeElement.click();
  }

  async onFileChange(event: any): Promise<void> {
    const files = event.target.files;
    const formData = new FormData();
    formData.append('logId', this.logIdString);
    for (const file of files) {
      formData.append('file', file, file.name);
    }
    this.httpClient.post<void>(`/be/upload_photo`, formData)
      .subscribe(ok => {
      });

    event.target.value = null;
  }

}
