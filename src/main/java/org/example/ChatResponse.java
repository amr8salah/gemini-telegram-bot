package org.example;

import com.google.gson.Gson;
import java.util.List;

class ChatResponse {
    List<Candidate> candidates;
    UsageMetadata usageMetadata;
    String modelVersion;
}

class Candidate {
    Content content;
    String finishReason;
    double avgLogprobs;
}

class Content {
    List<Part> parts;
    String role;
}

class Part {
    String text;
}

class UsageMetadata {
    int promptTokenCount;
    int candidatesTokenCount;
    int totalTokenCount;
    List<TokenDetails> promptTokensDetails;
    List<TokenDetails> candidatesTokensDetails;
}

class TokenDetails {
    String modality;
    int tokenCount;
}
