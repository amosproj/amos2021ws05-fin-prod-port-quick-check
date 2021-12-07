const card = {
  rounded: 'md',
  boxShadow: 'md',
};

const card_bordered = {
  ...card,
  border: '1px',
  borderColor: 'transparent',
  _hover: {
    boxShadow: 'lg',
    borderColor: 'primary',
  },
};

const card_clickable = {
  ...card,
  border: '10px',
  bg: 'primary',
  rounded: '3xl',
  color: 'white',
  _hover: {
      bg: 'bp.purple'
  }

}

const layerStyles = { card, card_bordered, card_clickable };

export default layerStyles;
