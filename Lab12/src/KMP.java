import java.util.LinkedList;

public class KMP implements IStringMatcher {

        static int[] computeLongestMatches(String pat, int patternLength)
        {
            int[] result = new int[patternLength];
            for (int i = 1, len = 0; i < patternLength; )
            {
                if (pat.charAt(i) == pat.charAt(len))
                {
                    result[i++] = ++len;
                }
                else
                {
                    if (len > 0)
                    {
                        len = result[len - 1];
                    }
                    else
                    {
                        i++;
                    }
                }
            }
            return result;
        }
    
	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		LinkedList<Integer> result = new LinkedList<>();

            if (text == null || pattern == null)
            {
                return result;
            }
            int patternLength = pattern.length();
            int textLength = text.length();
            int patternIndex = 0;
            int textIndex = 0;
            if (patternLength > textLength)
            {
                return result;
            }
            int[] matches = computeLongestMatches(pattern, patternLength);
            while (textIndex < textLength)
            {
                if (pattern.charAt(patternIndex) == text.charAt(textIndex))
                {
                    patternIndex++;
                    textIndex++;
                }
                if (patternIndex == patternLength)
                {
                    result.add(textIndex - patternIndex);
                    patternIndex = matches[patternIndex - 1];
                }
                else
                {
                    if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex))
                    {

                        if (patternIndex != 0)
                        {
                            patternIndex = matches[patternIndex - 1];
                        }

                        else
                        {
                            textIndex = textIndex + 1;
                        }

                    }
                }

            }
            return result;
	}

}