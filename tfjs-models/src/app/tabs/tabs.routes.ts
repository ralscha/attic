import {Routes} from '@angular/router';
import {TabsPage} from './tabs.page';
import {SpeechPage} from "../speech/speech.page";
import {PosenetPage} from "../posenet/posenet.page";
import {ObjectDetectionPage} from "../object-detection/object-detection.page";
import {MobilenetPage} from "../mobilenet/mobilenet.page";

export const routes: Routes = [
  {
    path: 'tabs',
    component: TabsPage,
    children: [
      {
        path: 'mobilenet',
        children: [
          {
            path: '',
            component: MobilenetPage
          }
        ]
      },
      {
        path: 'objectdetection',
        children: [
          {
            path: '',
            component: ObjectDetectionPage
          }
        ]
      },
      {
        path: 'posenet',
        children: [
          {
            path: '',
            component: PosenetPage
          }
        ]
      },
      {
        path: 'speech',
        children: [
          {
            path: '',
            component: SpeechPage
          }
        ]
      },
      {
        path: '',
        redirectTo: '/tabs/mobilenet',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    redirectTo: '/tabs/mobilenet',
    pathMatch: 'full'
  }
];
