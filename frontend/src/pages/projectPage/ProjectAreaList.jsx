import { React } from 'react';
import { Button, Heading, IconButton, Spacer, List, Flex } from '@chakra-ui/react';
import { DeleteIcon } from '@chakra-ui/icons';
import { useStoreActions, useStoreState } from 'easy-peasy';

import ConfirmClick from '../../components/ConfirmClick.jsx';

import AddAreaButton from './AddAreaButton.jsx';

/**
 * Show information for single product area
 * @param {dictionary} productArea - ProductArea information.
 */
function ProductArea({ productArea }) {
  const { projectID } = useStoreState((state) => state.project.data);

  return (
    <>
      <Button
        as="a"
        variant="secondary"
        rounded="md"
        href={`/projects/${projectID}/productArea/${productArea.id}`}
        w="full"
        py={5}
      >
        <Spacer />
        <Heading size="md" align="center">
          {productArea.name} ({productArea.category})
        </Heading>
        <Spacer />
      </Button>
    </>
  );
}
/**
 * List of Product Areas
 * @param {state} editMode - Is the user in edit mode or not.
 */
export default function ProductAreaList({ editMode }) {
  const productAreas = useStoreState((state) => state.project.data.productAreas);
  const removeProductArea = useStoreActions((actions) => actions.project.removeProductArea);

  return (
    <>
      <List w="full" maxW={500} alignItems="center" align="center" spacing={4} pb={5}>
        {productAreas.map((area) => (
          <Flex gridGap={2} w="full" align="center" key={area.id}>
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
