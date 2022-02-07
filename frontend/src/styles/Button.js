import { darken, whiten, mode } from '@chakra-ui/theme-tools';

export const ButtonStyles = {
  // Styles for the base style
  baseStyle: {
    rounded: '3xl',
    shadow: 'md',
  },
  // Styles for the size variations
  sizes: {},
  // Styles for the visual style variations
  variants: {
    primary: (props) => ({
      bg: mode('primary', whiten('primary', 85))(props),
      color: mode('white', darken('primary', 15))(props),
      px: 6,
      _hover: {
        borderColor: 'secondary',
        color: 'white',
        bg: 'secondary',
      },
    }),
    secondary: (props) => ({
      bg: mode('secondary', whiten('secondary', 85))(props),
      color: mode('white', darken('secondary', 15))(props),
      border: '1px',
      borderColor: 'transparent',
      px: 6,
      _hover: {
        color: 'white',
        bg: mode(whiten('secondary', 20), 'secondary')(props),
      },
    }),
    whisper: (props) => ({
      bg: 'transparent',
      border: '1px',
      px: 6,
      color: mode(props.color || 'secondary', 'white')(props),
      _hover: {
        border: '2px',
        color: mode('secondary', 'primary')(props),
      },
    }),
  },
  // The default `size` or `variant` values
  defaultProps: {
    // variant: 'whisper'
  },
};
