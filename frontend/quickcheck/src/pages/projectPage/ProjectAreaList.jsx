import { React } from 'react';
import { Button, Heading, IconButton, Spacer, List, Flex } from '@chakra-ui/react';
import { DeleteIcon } from '@chakra-ui/icons';
import { useStoreActions, useStoreState } from 'easy-peasy';

import ConfirmClick from '../../components/ConfirmClick.jsx';

import AddAreaButton from './AddAreaButton.jsx';

function ProductArea({ productArea }) {
  return (
    <>
      <Button as="a" variant="secondary" rounded="md" href="/projects" w="full" py={5}>
        <Spacer />
        <Heading size="md" align="center">
          {productArea.name}
        </Heading>
        <Spacer />
      </Button>
    </>
  );
}

export default function ProductAreaList({ editMode }) {
  const productAreas = useStoreState((state) => state.project.data.productAreas);
  const removeProductArea = useStoreActions((actions) => actions.project.removeProductArea);

  return (
    <>
      <List w="full" maxW={500} align="center" spacing={4} pb={5}>
      {productAreas.map((area) => (
        <Flex gridGap={2} w="full" align="center" key={area}>
          <ProductArea productArea={area} />
          {editMode ? (
            <ConfirmClick
              onConfirm={() => removeProductArea(area)}
              confirmPrompt="Remove this product area?"
            >
              <IconButton icon={<DeleteIcon />} variant="whisper" size="lg" />
            </ConfirmClick>
          ) : undefined}
        </Flex>
      ))}
        {editMode ? <AddAreaButton variant="primary" size="lg" w={32}></AddAreaButton> : <div />}
      </List>
    </>
  );
}
