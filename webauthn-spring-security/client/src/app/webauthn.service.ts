import {inject, Injectable, OnDestroy} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {lastValueFrom} from 'rxjs';
import {
  WebAuthnAuthenticationOptions,
  WebAuthnAuthenticationResponse,
  WebAuthnRegistrationOptions,
  WebAuthnRegistrationRequest,
  WebAuthnRegistrationResponse
} from './webauthn.types';

/**
 * WebAuthn Service for handling authentication and registration operations.
 * Provides methods for WebAuthn-based passwordless authentication.
 */
@Injectable({
  providedIn: 'root'
})
export class WebAuthnService implements OnDestroy {
  private abortController: AbortController | null = null;
  private readonly http = inject(HttpClient);

  /**
   * Authenticates a user using WebAuthn
   * @param headers Additional HTTP headers to send with requests
   * @returns Promise that resolves to the redirect URL on successful authentication
   */
  async authenticate(headers: Record<string, string> = {}): Promise<string> {
    let options: WebAuthnAuthenticationOptions;

    try {
      const optionsResponse = await lastValueFrom(this.http.post<WebAuthnAuthenticationOptions>(`${environment.baseUrl}/webauthn/authenticate/options`, null, {
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      }));

      if (!optionsResponse) {
        throw new Error('Failed to get authentication options');
      }

      options = optionsResponse;
    } catch (err: any) {
      const errorMessage = err?.error?.message || err?.message || 'Unknown error occurred';
      throw new Error(`Authentication failed. Could not fetch authentication options: ${errorMessage}`);
    }

    // Decode allowCredentials
    const decodedAllowCredentials = !options.allowCredentials ? [] :
      options.allowCredentials.map((cred: any) => ({
        ...cred,
        id: this.base64UrlDecode(cred.id)
      }));

    const decodedOptions = {
      ...options,
      allowCredentials: decodedAllowCredentials,
      challenge: this.base64UrlDecode(options.challenge)
    } as PublicKeyCredentialRequestOptions;

    const credentialOptions: CredentialRequestOptions = {
      publicKey: decodedOptions,
      signal: this.newAbortSignal()
    };


    let credential: PublicKeyCredential;
    try {
      credential = await navigator.credentials.get(credentialOptions) as PublicKeyCredential;
    } catch (err: any) {
      this.cleanupAbortController();
      throw new Error(`Authentication failed. Call to navigator.credentials.get failed: ${err.message}`);
    }

    const assertionResponse = credential.response as AuthenticatorAssertionResponse;
    let userHandle: string | undefined;
    if (assertionResponse.userHandle) {
      userHandle = this.base64UrlEncode(assertionResponse.userHandle);
    }

    const body = {
      id: credential.id,
      rawId: this.base64UrlEncode(credential.rawId),
      response: {
        authenticatorData: this.base64UrlEncode(assertionResponse.authenticatorData),
        clientDataJSON: this.base64UrlEncode(assertionResponse.clientDataJSON),
        signature: this.base64UrlEncode(assertionResponse.signature),
        userHandle
      },
      type: credential.type,
      clientExtensionResults: credential.getClientExtensionResults(),
      authenticatorAttachment: (credential as any).authenticatorAttachment
    };

    let authenticationResponse: WebAuthnAuthenticationResponse;
    try {
      const serverResponse = await lastValueFrom(this.http.post<WebAuthnAuthenticationResponse>(`${environment.baseUrl}/login/webauthn`, body, {
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      }));

      if (!serverResponse) {
        throw new Error('No response received from server');
      }

      authenticationResponse = serverResponse;
    } catch (err: any) {
      this.cleanupAbortController();
      const errorMessage = err?.error?.message || err?.message || 'Unknown error occurred';
      throw new Error(`Authentication failed. Could not process the authentication request: ${errorMessage}`);
    }

    this.cleanupAbortController();

    if (!(authenticationResponse?.authenticated && authenticationResponse?.redirectUrl)) {
      throw new Error(
        `Authentication failed. Expected {"authenticated": true, "redirectUrl": "..."}, server responded with: ${JSON.stringify(authenticationResponse)}`
      );
    }

    return authenticationResponse.redirectUrl;
  }

  /**
   * Registers a new WebAuthn credential for the user
   * @param headers Additional HTTP headers to send with requests
   * @param label Label for the passkey (required)
   * @returns Promise that resolves when registration is complete
   */
  async register(headers: Record<string, string> = {}, label?: string): Promise<void> {
    if (!label) {
      throw new Error("Error: Passkey Label is required");
    }

    let options: WebAuthnRegistrationOptions;
    try {
      const optionsResponse = await lastValueFrom(this.http.post<WebAuthnRegistrationOptions>(`${environment.baseUrl}/webauthn/register/options`, null, {
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      }));

      if (!optionsResponse) {
        throw new Error('Failed to get registration options');
      }

      options = optionsResponse;
    } catch (err: any) {
      const errorMessage = err?.error?.message || err?.message || 'Unknown error occurred';
      throw new Error(`Registration failed. Could not fetch registration options: ${errorMessage}`);
    }

    // Decode excludeCredentials
    const decodedExcludeCredentials = !options.excludeCredentials ? [] :
      options.excludeCredentials.map((cred: any) => ({
        ...cred,
        id: this.base64UrlDecode(cred.id)
      }));

    const decodedOptions = {
      ...options,
      user: {
        ...options.user,
        id: this.base64UrlDecode(options.user.id)
      },
      challenge: this.base64UrlDecode(options.challenge),
      excludeCredentials: decodedExcludeCredentials
    } as PublicKeyCredentialCreationOptions;

    let credentialsContainer: PublicKeyCredential;
    try {
      credentialsContainer = await navigator.credentials.create({
        publicKey: decodedOptions,
        signal: this.newAbortSignal()
      }) as PublicKeyCredential;
    } catch (err: any) {
      this.cleanupAbortController();
      throw new Error(`Registration failed. Call to navigator.credentials.create failed: ${err.message}`);
    }

    const attestationResponse = credentialsContainer.response as AuthenticatorAttestationResponse;
    const credential = {
      id: credentialsContainer.id,
      rawId: this.base64UrlEncode(credentialsContainer.rawId),
      response: {
        attestationObject: this.base64UrlEncode(attestationResponse.attestationObject),
        clientDataJSON: this.base64UrlEncode(attestationResponse.clientDataJSON),
        transports: (attestationResponse as any).getTransports ? (attestationResponse as any).getTransports() : []
      },
      type: credentialsContainer.type,
      clientExtensionResults: credentialsContainer.getClientExtensionResults(),
      authenticatorAttachment: (credentialsContainer as any).authenticatorAttachment
    };

    const registrationRequest: WebAuthnRegistrationRequest = {
      publicKey: {
        credential,
        label
      }
    };

    let verificationResponse: WebAuthnRegistrationResponse;
    try {
      const serverResponse = await lastValueFrom(this.http.post<WebAuthnRegistrationResponse>(`${environment.baseUrl}/webauthn/register`, registrationRequest, {
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      }));

      if (!serverResponse) {
        throw new Error('No response received from server');
      }

      verificationResponse = serverResponse;
    } catch (err: any) {
      this.cleanupAbortController();
      const errorMessage = err?.error?.message || err?.message || 'Unknown error occurred';
      throw new Error(`Registration failed. Could not process the registration request: ${errorMessage}`);
    }

    this.cleanupAbortController();

    if (!verificationResponse?.success) {
      throw new Error(`Registration failed. Server responded with: ${JSON.stringify(verificationResponse)}`);
    }
  }

  /**
   * Angular lifecycle hook - cleanup when service is destroyed
   */
  ngOnDestroy(): void {
    this.cleanupAbortController();
  }

  /**
   * Encodes an ArrayBuffer to a base64url string
   */
  private base64UrlEncode(buffer: ArrayBuffer): string {
    const base64 = window.btoa(String.fromCharCode(...new Uint8Array(buffer)));
    return base64.replace(/=/g, "").replace(/\+/g, "-").replace(/\//g, "_");
  }

  /**
   * Decodes a base64url string to an ArrayBuffer
   */
  private base64UrlDecode(base64url: string): ArrayBuffer {
    // Add padding if needed
    let base64 = base64url.replace(/-/g, "+").replace(/_/g, "/");
    while (base64.length % 4) {
      base64 += "=";
    }
    const binStr = window.atob(base64);
    const bin = new Uint8Array(binStr.length);
    for (let i = 0; i < binStr.length; i++) {
      bin[i] = binStr.charCodeAt(i);
    }
    return bin.buffer;
  }

  /**
   * Creates a new AbortSignal for WebAuthn operations, cancelling any existing operation
   */
  private newAbortSignal(): AbortSignal {
    if (this.abortController) {
      this.abortController.abort("Initiating new WebAuthn ceremony, cancelling current ceremony");
    }
    this.abortController = new AbortController();
    return this.abortController.signal;
  }

  /**
   * Cleans up the current AbortController
   */
  private cleanupAbortController(): void {
    if (this.abortController) {
      this.abortController = null;
    }
  }

}
