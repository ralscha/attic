export class SettingsService {
  private alternativeBackground: boolean;

  toggleAlternativeBackground(flag: boolean) {
    this.alternativeBackground = flag;
  }

  isAlternativeBackground() {
    return this.alternativeBackground;
  }

}
