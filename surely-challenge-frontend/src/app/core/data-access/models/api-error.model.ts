/** Mirrors GlobalExceptionHandler's ErrorResponseDTO on the backend. */
export interface ApiError {
  timestamp: string;
  status: number;
  error: string;
  message: string;
}
