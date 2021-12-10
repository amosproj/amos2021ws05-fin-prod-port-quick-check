import { mode } from '@chakra-ui/theme-tools';

const input_sizes = {
  xl: {
    fontSize: 'xl',
    borderRadius: 'lg',
    px: 4,
    h: 12,
  },
  xxl: {
    fontSize: '2xl',
    borderRadius: 'lg',
    px: 4,
    h: 14,
  },
  xxxl: {
    fontSize: '3xl',
    borderRadius: 'xl',
    px: 4,
    h: 16,
  },
};

export const Input = {
  // Styles for the base style
  baseStyle: {
    field: {
      _disabled: { bg: 'transparent' },
    },
    addon: {
      _disabled: { bg: 'transparent' },
    },
  },

  // Styles for the visual style variations
  variants: {
    foo: {
      color: 'red',
    },
    heading: (props) => ({
      field: {
        fontWeight: 'bold',
        py: 7,
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
