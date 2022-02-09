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
      _invalid: { borderColor: 'red' },
    },
    addon: {
      _disabled: { bg: 'transparent' },
      _invalid: { borderColor: 'red' },
    },
  },
  // Styles for the size variations
  sizes: {
    xl: { field: input_sizes.xl, addon: input_sizes.xl },
    '2xl': { field: input_sizes.xxl, addon: input_sizes.xxl },
    '3xl': { field: input_sizes.xxxl, addon: input_sizes.xxxl },
  },

  // Styles for the visual style variations
  variants: {
    autoColor: (props) => ({
      field: {
        bg: mode('gray.200', 'gray.600')(props),
      },
      addon: {
        bg: mode('gray.200', 'gray.600')(props),
      },
    }),
    bold: (props) => ({
      field: {
        fontWeight: 'bold',
        bg: mode('gray.200', 'gray.600')(props),
      },
    }),
  },
  // The default `size` or `variant` values
  defaultProps: {
    variant: 'autoColor',
  },
};
