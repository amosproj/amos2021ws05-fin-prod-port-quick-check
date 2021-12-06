import { darken, whiten, mode} from '@chakra-ui/react'



export const FlexStyle = {
    // Styles for the base style
    // Styles for the size variations
    sizes: {},
    // Styles for the visual style variations
    variants: {
      card: (props) => ({
        bg: mode('brand.300', 'brand.600')(props),
        _hover: {
          bg: mode(darken('brand.300', 20), whiten('brand.600', 20))(props),
          transform: 'scale(1.25)'
        }
      }),
    },
    // The default `size` or `variant` values
    defaultProps: {},
  }



