import { mode } from '@chakra-ui/theme-tools';

export const Text = {
  // Styles for the base style
  baseStyle: {
    baseStyle: (props) => ({
      color: 'text',
      align: 'left',
    }),
  },
  // Styles for the size variations
  sizes: {},
  // Styles for the visual style variations
  variants: {
    cell: (props) => ({
      p: '2',
      px: '4',
      bg: mode('gray.200', 'gray.600')(props),
      rounded: 'md',
    }),
  },
  // The default `size` or `variant` values
  defaultProps: {
    // variant: 'whisper'
  },
};
