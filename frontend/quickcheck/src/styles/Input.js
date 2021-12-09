import { mode } from '@chakra-ui/theme-tools';

export const Input = {
  // Styles for the base style
  baseStyle: {},
  // Styles for the size variations
  sizes: {},
  // Styles for the visual style variations
  variants: {
    foo: {
      color: 'red',
    },
    heading: (props) => ({
      field: {
        fontWeight: 'bold',
        fontSize: '30',
        py: '7',
        w: 'wrap',
        bg: mode('gray.200', 'gray.600')(props),
        _disabled: {
          bg: 'transparent',
        },
      },
    }),
  },
  // The default `size` or `variant` values
  defaultProps: {},
};
